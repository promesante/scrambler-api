(ns scrambler.core
  (:require [mount.core :as mount])
  (:gen-class))

(defn -main
  [& args]
  (println "\nCreating your server...")
  (mount/start))
