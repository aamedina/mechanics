(ns mechanics.generic)

(defn arity
  [procedure]
  (when-let [{:keys [arglists] :as metadata} (meta (resolve procedure))]
    (let [[min-args max-args] (apply (juxt min max) (map count arglists))
          variadic? (contains? (set (last arglists)) '&)]
      [min-args (if variadic? false max-args)])))
