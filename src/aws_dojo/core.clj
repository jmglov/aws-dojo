(ns aws-dojo.core
  (:require [amazonica.core :as aws]
            [environ.core :refer [env]]))

(defn authenticate! []
  (aws/defcredential
    (env :aws-access-key-id) (env :aws-secret-access-key) (env :aws-region)))
