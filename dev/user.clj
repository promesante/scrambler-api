(ns user
  (:require [clojure.tools.namespace.repl :as tn]
            [mount.core :as mount]
            [io.pedestal.http :as http]
            [io.pedestal.test :as test]
            [scrambler.server :as server]))

(defn start []
  (mount/start))

(defn stop []
  (mount/stop))

(defn reset
  "stops all states defined by defstate, reloads modified source files, and restarts the states"
  []
  (stop)
  (tn/refresh :after 'user/go))

(mount/in-clj-mode)

;###############################################################
;
; Utils
;
;###############################################################

(defn test-request [verb url]
  (test/response-for (::http/service-fn server/server) verb url))

(defn scramble-1 []
  (test/response-for (::http/service-fn server/server) :post "/scrambler/scramble" :headers {"Content-Type" "application/json"} :body "{\"superset\": \"rekqodlw\", \"subset\": \"world\"}"))

(defn scramble-2 []
  (test/response-for (::http/service-fn server/server) :post "/scrambler/scramble" :headers {"Content-Type" "application/json"} :body "{\"superset\": \"cedewaraaossoqqyt\", \"subset\": \"codewars\"}"))

(defn scramble-3 []
  (test/response-for (::http/service-fn server/server) :post "/scrambler/scramble" :headers {"Content-Type" "application/json"} :body "{\"superset\": \"katas\", \"subset\": \"steak\"}"))

(defn scramble-4 []
  (test/response-for (::http/service-fn server/server) :post "/scrambler/scramble" :headers {"Content-Type" "application/json"} :body "{\"superset\": \"Rekqodlw\", \"subset\": \"world\"}"))

(defn scramble-5 []
  (test/response-for (::http/service-fn server/server) :post "/scrambler/scramble" :headers {"Content-Type" "application/json"} :body "{\"superset\": \"rekqodlw1\", \"subset\": \"world\"}"))

(defn scramble-6 []
  (test/response-for (::http/service-fn server/server) :post "/scrambler/scramble" :headers {"Content-Type" "application/json"} :body "{\"superset\": \"rekqodlw!\", \"subset\": \"world\"}"))

(defn scramble-7 []
  (test/response-for (::http/service-fn server/server) :post "/scrambler/scramble" :headers {"Content-Type" "application/json"} :body "{\"superset\": \"rekqodlw\", \"subset\": \"World\"}"))

(defn scramble-8 []
  (test/response-for (::http/service-fn server/server) :post "/scrambler/scramble" :headers {"Content-Type" "application/json"} :body "{\"superset\": \"rekqodlw\", \"subset\": \"world1\"}"))

(defn scramble-9 []
  (test/response-for (::http/service-fn server/server) :post "/scrambler/scramble" :headers {"Content-Type" "application/json"} :body "{\"superset\": \"rekqodlw\", \"subset\": \"world!\"}"))

(defn scramble-10 []
  (test/response-for (::http/service-fn server/server) :post "/scrambler/scramble" :headers {"Content-Type" "application/json"} :body "{\"subset\": \"world\"}"))

(defn scramble-11 []
  (test/response-for (::http/service-fn server/server) :post "/scrambler/scramble" :headers {"Content-Type" "application/json"} :body "{\"superset\": \"rekqodlw\"}"))
