(ns mechanics.generic
  (:refer-clojure :exclude [* / + -])
  (:require [mechanics.protocols :as impl]))

(defn- >1? [n] (clojure.lang.Numbers/gt n 1))
(defn- >0? [n] (clojure.lang.Numbers/gt n 0))

(defn- nary-inline
  [op]
  (fn ([x] `(if (satisfies? mechanics.protocols/Num ~x)
              ~x
              (throw (ex-info "Non-numeric argument given to numeric op" {}))))
    ([x y] `(~op ~x ~y))
    ([x y & more] (reduce (fn [a b] `(~op ~a ~b)) `(~op ~x ~y) more))))

(defn +
  {:inline (nary-inline 'mechanics.protocols/plus)
   :inline-arities >1?}
  ([] 0)
  ([x] {:pre [(satisfies? impl/Num x)]} x)
  ([x y] (impl/plus x y))
  ([x y & more] (reduce impl/plus (impl/plus x y) more)))

(defn *
  {:inline (nary-inline 'mechanics.protocols/mult)
   :inline-arities >1?}
  ([] 1)
  ([x] {:pre [(satisfies? impl/Num x)]} x)
  ([x y] (impl/mult x y))
  ([x y & more] (reduce impl/mult (impl/mult x y) more)))

(defn /
  {:inline (nary-inline 'mechanics.protocols/div)
   :inline-arities >1?}
  ([x] (impl/div (impl/one-like x) x))
  ([x y] (impl/div x y))
  ([x y & more] (reduce impl/div (impl/div x y) more)))

(defn -
  {:inline (nary-inline 'mechanics.protocols/sub)
   :inline-arities >1?}
  ([x] (impl/sub (impl/zero-like x) x))
  ([x y] (impl/sub x y))
  ([x y & more] (reduce impl/sub (impl/sub x y) more)))

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

(defn negate
  [x]
  (- (impl/zero-like x) x))
