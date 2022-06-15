(ns scrambler.server
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [ring.middleware.cors :as cors]
            [mount.core :as mount :refer [defstate]]
            [scrambler.routes :as r]))

(def service-map
  {::http/routes r/routes
   ::http/type :jetty
   ::http/port 8890
   ::http/allowed-origins ["http://localhost:8280"
                           ; "http://localhost:8890"
                           ]
   })

(defstate server
  :start (http/start (http/create-server (assoc service-map ::http/join? false)))
  :stop (http/stop server))
