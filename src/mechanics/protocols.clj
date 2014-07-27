(ns mechanics.protocols)

(defprotocol TypePredicate
  (type-predicate [x]))

(extend-protocol TypePredicate
  Number
  (type-predicate [x] number?)
  
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
