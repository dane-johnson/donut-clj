(ns donut.core
  (:require [reagent.core :as reagent :refer [atom]]
            [goog.string.format]
            [goog.string :refer [format]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce height (atom 5.75))
(def donut-height 0.3)

(defn calc-donuts
  []
  (/ @height donut-height))
(defn slider []
  [:div {:class "slider"}
   [:input {:type "range" :value @height :min 0.3 :max 14.0 :step 0.05
            :on-change #(reset! height (-> % .-target .-value))}]
   [:p (format "%.2f ft. -> %.2f donuts" @height (calc-donuts))]])

(defn donut-region []
  (let [size (/ 300 (calc-donuts))]
    [:div {:class "donuts"}
     [:ul
      (for [i ((comp range inc int) (calc-donuts))]
        ^{:key i} [:li {:style {:height size}} [:img {:src "./img/donut.png" :height size}]])]]))

(defn sil-region []
  [:div {:class "sils"}
   [:img {:src (cond
                 (>= @height 12) "./img/trex.png"
                 (>= @height 1) "./img/man.png"
                 :default "./img/ant.png")
          :height "300px"}]])

(defn hello-world []
  [:div [:h1 "Whats my height in donuts?"] [slider] [donut-region] [sil-region]])

(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))
