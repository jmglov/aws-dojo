(ns aws-dojo.sqs
  (:require [amazonica.aws.sqs :as sqs]
            [cheshire.core :as json]
            [clojure.spec :as s]))

(s/def :sqs/name string?)
(s/def :sqs/url string?)

(s/def :sqs/queue (s/keys :req [:sqs/name
                                :sqs/url]))

(s/def :sqs/message-id string?)
(s/def :sqs/message-body any?)

(s/def :sqs/message (s/keys :req [:sqs/message-id]
                            :opt [:sqs/message-body]))

(s/fdef create-queue!
        :args (s/cat :name :sqs/queue-name)
        :ret :sqs/queue)

(defn create-queue! [name])

(s/fdef send-message
        :args (s/cat :queue :sqs/queue
                     :msg string?)
        :ret :sqs/message)

(defn send-message [queue msg])

(s/fdef receive-message
        :args (s/cat :queue :sqs/queue)
        :ret (s/nilable :sqs/message))

(defn receive-message [queue])
