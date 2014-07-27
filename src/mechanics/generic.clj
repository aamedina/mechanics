(ns mechanics.generic)

(defn arglists
  [x]
  (cond
    (symbol? x) (:arglists (meta (resolve x)))

    (ifn? x) (->> (.getDeclaredMethods (class x))
                  (filter #(= (.getName %) "invoke"))
                  (map #(.getParameterTypes %)))

    :else nil))

(defn variadic?
  [x]
  (or (and (symbol? x) (contains? (set (last (arglists x))) '&))
      (and (fn? x) (instance? clojure.lang.RestFn x))))

(defn arity
  [x]
  (when-let [args (arglists x)]
    (let [[min-args max-args] (apply (juxt min max) (map count args))]
      [min-args (if (variadic? x) false max-args)])))
