(ns mechanics.generic
  (:refer-clojure :exclude [* / + -])
  (:require [mechanics.protocols :as impl]))

(defn numeric?
  [x]
  (satisfies? impl/Num x))

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
  ([x] x)
  ([x y] (impl/plus x y))
  ([x y & more] (reduce impl/plus (impl/plus x y) more)))

(defn *
  {:inline (nary-inline 'mechanics.protocols/mult)
   :inline-arities >1?}
  ([] 1)
  ([x] x)
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

(defn invert
  [x]
  (/ (impl/one-like x) x))

(definline sqrt
  [x]
  `(Math/sqrt ~x))

(definline square
  [x]
  `(* ~x ~x))

(definline cube
  [x]
  `(* ~x ~x ~x))

(definline gcd
  [x val]
  (if (and (numeric? x) (numeric? val))
    (.gcd (biginteger x) (biginteger val))
    `(.gcd (biginteger ~x) (biginteger ~val))))

(definline abs
  [x]
  `(impl/abs ~x))

(definline exp
  [x]
  `(impl/exp ~x))

(definline exp10
  [x]
  (if (numeric? x)
    `(* ~@(repeat x 10))
    `(apply * (repeat ~x 10))))

(definline exp2
  [x]
  (if (numeric? x)
    `(* ~@(repeat x 2))
    `(apply * (repeat ~x 2))))

(definline log
  [x]
  `(impl/log ~x))

(definline log10
  [x]
  `(/ (log ~x) ~(log 10)))

(definline log2
  [x]
  `(/ (log ~x) ~(log 2)))

(definline sin
  [x]
  `(impl/sin ~x))

(definline cos
  [x]
  `(impl/cos ~x))

(definline tan
  [x]
  `(impl/tan ~x))

(definline sec
  [x]
  `(/ 1 (cos ~x)))

(definline csc
  [x]
  `(/ 1 (sin ~x)))

(definline asin
  [x]
  `(impl/asin ~x))

(definline acos
  [x]
  `(impl/acos ~x))

(definline atan
  [x]
  `(impl/atan ~x))

(definline sinh
  [x]
  `(impl/sinh ~x))

(definline cosh
  [x]
  `(impl/cosh ~x))

(definline tanh
  [x]
  `(/ (sinh ~x) (cosh ~x)))

(definline sech
  [x]
  `(/ 1 (cosh ~x)))

(definline csch
  [x]
  `(/ 1 (sinh ~x)))

(defn make-rectangular
  [a1 a2])

(defn make-polar
  [a1 a2])

(defn real-part
  [z])

(defn imag-part
  [z])

(defn magnitude
  [z])

(defn angle
  [z])

(defn conjugate
  [z])

(defn determinant
  [mat])

(defn trace
  [mat])

