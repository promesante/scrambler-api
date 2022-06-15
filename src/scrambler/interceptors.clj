(ns scrambler.interceptors
  (:require [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http :as bootstrap]
            [io.pedestal.http :as http]
            [io.pedestal.interceptor.chain :as chain]))

(defn response [status body & {:as headers}]
  {:status status :body body :headers headers})

(def ok       (partial response 200))
(def created  (partial response 201))
(def accepted (partial response 202))

(def common-interceptors [(body-params/body-params) http/html-body bootstrap/json-body])

(def entity-render
  {:name :entity-render
   :leave
   (fn [context]
     (if-let [item (:result context)]
       (assoc context :response (ok item))
       context))})

(defn validate-param-available-by-attr-key [attr-key]
   (fn [context]
     (if-let [param (get-in context [:request :json-params attr-key])]
       context
       (chain/terminate
        (assoc context :response
               {:status 400
                :body (str "Param "
                           (name attr-key)
                           " is mandatory, but not available in request body")})))))

(def validate-param-available-superset
  {:name :validate-param-available-superset
   :enter (validate-param-available-by-attr-key :superset)})

(def validate-param-available-subset
  {:name :validate-param-available-subset
   :enter (validate-param-available-by-attr-key :subset)})

(defn validate-just-chars [param]
  (some? (re-matches #"^[a-z]+$" param)))

(defn validate-just-chars-by-attr-key [attr-key]
  (fn [context]
    (let [attr-value (get-in context [:request :json-params attr-key])]
      (if (validate-just-chars attr-value)
        context
        (chain/terminate
         (assoc context :response
                {:status 400
                 :body (str "Param "
                            (name attr-key)
                            " must hold only lower case chars")}))))))

(def validate-just-chars-superset
  {:name :validate-just-chars-superset
   :enter (validate-just-chars-by-attr-key :superset)})

(def validate-just-chars-subset
  {:name :validate-just-chars-superset
   :enter (validate-just-chars-by-attr-key :subset)})

(defn scramble? [superset subset]
  (let [sbs (seq subset)
        sps (seq superset)]
    (every? (fn [ch] (some #(= ch %) sps)) sbs)))

(def scramble
  {:name :scramble
   :enter
   (fn [context]
     (let [{:keys [superset subset]} (get-in context [:request :json-params])
           result-vaue (scramble? superset subset)
           result {:scramble? result-vaue}]
       (assoc context :result result)))})
