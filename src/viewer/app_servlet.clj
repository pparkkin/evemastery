(ns viewer.app_servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use viewer.core)
  (:use [appengine-magic.servlet :only [make-servlet-service-method]]))


(defn -service [this request response]
  ((make-servlet-service-method viewer-app) this request response))
