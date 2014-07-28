(ns mechanics.protocols
  (:refer-clojure :exclude [zero?]))

(defprotocol Num
  (plus [x y])
  (sub [x y])
  (mult [x y])
  (div [x y]))

(defprotocol TypePredicate
  (type-predicate [x]))

(defprotocol ZeroLike
  (zero-like [x]))

(defprotocol OneLike
  (one-like [x]))

(defprotocol AdditiveIdentity
  (zero? [x]))

(defprotocol MultiplicativeIdentity
  (one? [x]))

(extend-type Number
  Num
  (plus [x y] (+ x y))
  (mult [x y] (* x y))
  (sub [x y] (- x y))
  (div [x y] (/ x y))
  
  TypePredicate
  (type-predicate [x] number?)
  
  ZeroLike
  (zero-like [x] 0)
  
  OneLike
  (one-like [x] 1)

  AdditiveIdentity
  (zero? [x] (clojure.core/zero? x))

  MultiplicativeIdentity
  (one? [x] (== x 1)))

(extend-protocol TypePredicate
  Float
  (type-predicate [x] float?)

  Double
  (type-predicate [x] float?)

  Integer
  (type-predicate [x] integer?)

  Long
  (type-predicate [x] integer?)

  Short
  (type-predicate [x] integer?)

  java.math.BigInteger
  (type-predicate [x] integer?)

  java.math.BigDecimal
  (type-predicate [x] decimal?)

  clojure.lang.Ratio
  (type-predicate [x] ratio?))
