(ns scrambler.routes
  (:require [scrambler.interceptors :as i]))

(def scramble
  (into [] (concat i/common-interceptors
                   [i/entity-render
                    i/validate-param-available-superset
                    i/validate-param-available-subset
                    i/validate-just-chars-superset
                    i/validate-just-chars-subset
                    i/scramble])))

(def routes
  #{["/scrambler/scramble" :post scramble
                           :route-name :scramble]})
