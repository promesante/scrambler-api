(ns scrambler.scrambler-test
  (:require [clojure.test :refer :all]
            [scrambler.scrambler :refer :all]))

(deftest test-validate-string
  (testing "testing validate-string function"
    (let [param "test"]
      (is (= param (validate-string param))))
    (let [param 123]
      (is (= nil (validate-string param))))))

(deftest test-validate-just-chars
  (testing "testing validate-just-chars function"
    (let [param "test"]
      (is (= param (validate-just-chars param))))
    (let [param "Test"]
      (is (= nil (validate-just-chars param))))
    (let [param "test1"]
      (is (= nil (validate-just-chars param))))
    (let [param "test!"]
      (is (= nil (validate-just-chars param))))))

(deftest test-validate
  (testing "testing validate function"
    (let [param "test"]
      (is (= true (validate param))))
    (let [param "Test"]
      (is (= false (validate param))))
    (let [param "test1"]
      (is (= false (validate param))))
    (let [param "test!"]
      (is (= false (validate param))))
    (let [param 123]
      (is (= false (validate param))))))

(deftest test-scramble?
  (testing "testing scramble"
    (is (= true (scramble? "rekqodlw" "world")))
    (is (= true (scramble? "cedewaraaossoqqyt" "codewars")))
    (is (= false (scramble? "katas" "steak")))
    (is (= false (scramble? "Rekqodlw" "world")))
    (is (= false (scramble? "rekqodlw1" "world")))
    (is (= false (scramble? "rekqodlw!" "world")))
    (is (= false (scramble? 123 "world")))
    (is (= false (scramble? "rekqodlw" "World")))
    (is (= false (scramble? "rekqodlw" "world1")))
    (is (= false (scramble? "rekqodlw" "world!")))
    (is (= false (scramble? "rekqodlw" 123)))))
