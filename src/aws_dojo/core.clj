(ns aws-dojo.core
  (:require [amazonica.core :as aws]
            [aws-dojo.drawing :as drawing]
            [aws-dojo.s3 :as s3]
            [aws-dojo.sqs :as sqs]
            [environ.core :refer [env]]))

(defn authenticate! []
  (aws/defcredential
    (env :aws-access-key-id) (env :aws-secret-access-key) (env :aws-region)))

(defn run [queue-name bucket-name]
  (authenticate!))
