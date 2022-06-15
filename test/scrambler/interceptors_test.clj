(ns scrambler.interceptors-test
  (:require [clojure.test :refer :all]
            [scrambler.interceptors :refer :all]))

(deftest test-scramble?
  (testing "testing scramble?"
    (is (= true (scramble? "rekqodlw" "world")))
    (is (= true (scramble? "cedewaraaossoqqyt" "codewars")))
    (is (= false (scramble? "katas" "steak")))))

(deftest test-scramble
  (testing "testing scramble"
    (let [context-1 {:request {:json-params {:superset "rekqodlw"
                                             :subset "world"}}}
          context-2 {:request {:json-params {:superset "cedewaraaossoqqyt"
                                             :subset "codewars"}}}
          context-3 {:request {:json-params {:superset "katas"
                                             :subset "steak"}}}]
      (is (= (assoc context-1 :result {:scramble? true}) ((:enter scramble) context-1)))
      (is (= (assoc context-2 :result {:scramble? true}) ((:enter scramble) context-2)))
      (is (= (assoc context-3 :result {:scramble? false}) ((:enter scramble) context-3))))))

(deftest test-validate-just-chars
  (testing "testing validate-just-chars"
    (is (= true (validate-just-chars "world")))
    (is (= false (validate-just-chars "world1")))
    (is (= false (validate-just-chars "World")))))

(deftest test-validate-just-chars-interceptors
  (testing "testing validate-just-chars interceptors"
    (let [context-1 {:request {:json-params {:superset "rekqodlw"
                                             :subset "world"}}}
          context-2 {:request {:json-params {:superset "cedewaraaossoqqyt"
                                             :subset "codewars"}}}
          context-3 {:request {:json-params {:superset "katas"
                                             :subset "steak"}}}
          context-4 {:request {:json-params {:superset "Rekqodlw"
                                             :subset "world"}}}
          context-5 {:request {:json-params {:superset "rekqodlw1"
                                             :subset "world"}}}
          context-6 {:request {:json-params {:superset "rekqodlw!"
                                             :subset "world"}}}
          context-7 {:request {:json-params {:superset "rekqodlw"
                                             :subset "World"}}}
          context-8 {:request {:json-params {:superset "rekqodlw"
                                             :subset "world1"}}}
          context-9 {:request {:json-params {:superset "rekqodlw"
                                             :subset "world!"}}}]
      (is (= context-1 ((:enter validate-just-chars-superset) context-1)))
      (is (= context-1 ((:enter validate-just-chars-subset) context-1)))
      (is (= context-2 ((:enter validate-just-chars-superset) context-2)))
      (is (= context-2 ((:enter validate-just-chars-subset) context-2)))
      (is (= context-3 ((:enter validate-just-chars-superset) context-3)))
      (is (= context-3 ((:enter validate-just-chars-subset) context-3)))
      (is (= {:status 400 :body "Param superset must hold only lower case chars"}
             (:response ((:enter validate-just-chars-superset) context-4))))
      (is (= {:status 400 :body "Param superset must hold only lower case chars"}
             (:response ((:enter validate-just-chars-superset) context-5))))
      (is (= {:status 400 :body "Param superset must hold only lower case chars"}
             (:response ((:enter validate-just-chars-superset) context-6))))
      (is (= {:status 400 :body "Param subset must hold only lower case chars"}
             (:response ((:enter validate-just-chars-subset) context-7))))
      (is (= {:status 400 :body "Param subset must hold only lower case chars"}
             (:response ((:enter validate-just-chars-subset) context-8))))
      (is (= {:status 400 :body "Param subset must hold only lower case chars"}
             (:response ((:enter validate-just-chars-subset) context-9)))))))

(deftest test-validate-param-available
  (testing "testing validate-param-available interceptors"
    (let [context-1 {:request {:json-params {:superset "rekqodlw"
                                             :subset "world"}}}
          context-2 {:request {:json-params {:subset "world"}}}
          context-3 {:request {:json-params {:superset "rekqodlw"}}}]
      (is (= context-1 ((:enter validate-param-available-superset) context-1)))
      (is (= {:status 400 :body "Param superset is mandatory, but not available in request body"}
             (:response ((:enter validate-param-available-superset) context-2))))
      (is (= {:status 400 :body "Param subset is mandatory, but not available in request body"}
             (:response ((:enter validate-param-available-subset) context-3))))
    )))
