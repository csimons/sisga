#!/usr/bin/env clisp

;;; CLS, August 2011
;;;
;;; A purely-functional implementation of a simple evolutionary computation
;;; system for optimizing single-variable real-valued functions.
;;;
;;; Parent selection: Two random higher-than-average-fitness genes selected.
;;; Recombination:    Random one-point crossover.
;;; Mutation:         Simple creeping/incremental (+/- some { x | 0 < x < 1 }.
;;;
;;; Truncation (elitist) and tournament mechanisms implemented (these can be
;;; swapped in the 'evolve' function).
;;;
;;; Run as follows:
;;;
;;; ./lev.lisp -100.0 100.0 10 2 "(lambda (x) (- (* x x)))" 100
;;;
;;; The above would sample solution space [-100, 100] and would use
;;; a stable/parent population size of 10 individuals and a reproduction
;;; rate of 2 offspring per generation.  The fitness function here
;;; is -(x^2) (using Lisp syntax), and the program will run for 100
;;; new generations.  To see an explanation of the command-line
;;; parameters, just run the program without specifying any, and
;;; a usage message will be displayed.
;;;
;;; The GNU Common Lisp distribution must be installed to run.
;;;

;; Remove the first instance of an item from a list.
;;
(defun remove-first (x alist)
  (if (= 0 (length alist))
    '()
    (if (eql x (car alist))
      (cdr alist)
      (cons (car alist) (remove-first x (cdr alist))))))

;; Non-destructive sort, using 'comp-fn' parameter as comparator.
;;
;; Comparator function should be something like 'max' or 'min', that
;; can return the 'top item' given several numbers.
;;
(defun nsort (comp-fn alist &optional (result '()))
  (if (= 0 (length alist))
    result
    (let ((top-elem (apply comp-fn alist)))
      (nsort comp-fn
             (remove-first top-elem alist)
             (append result (list top-elem))))))

;; Return the first n items of a list.
;;
(defun first-n (n alist &optional result)
  (if (= n (length result))
    result
    (first-n n (cdr alist) (append result (list (car alist))))))

;; Remove the first instance of an item from a list.
;;
(defun remove-first (x alist)
  (if (= 0 (length alist))
    '()
    (if (eql x (car alist))
      (cdr alist)
      (cons (car alist) (remove-first x (cdr alist))))))

;; Get a list of n real random numbers between smin and smax; note that
;; passing in floating-point numbers will allow for floating-point
;; individuals, while using integers will result in integer individuals.
;;
(defun get-rand-popl (smin smax n &optional (popl '()))
  (if (= 0 n)
    popl
    (get-rand-popl smin
                   smax
                   (- n 1)
                   (append popl
                           (list (+ smin (random (+ 1 (- smax smin)))))))))

;; Get a list of n new offspring from a population.
;;
;; Currently, new offspring are derived by adding some value x between
;; -1 and +1 to the parent.  This could be replaced by an appropriate
;; mutation algorithm that allows new offspring to more quickly scale
;; the solution space.
;;
(defun reproduce (popl m n smin smax)
  (if (= n 0)
    '()
    (let ((index (random m))
          (direction (random 2)))
      (if (= 1 direction)
        (cons (+ (nth index popl) (random 1.0))
              (reproduce popl m (- n 1) smin smax))
        (cons (- (nth index popl) (random 1.0))
              (reproduce popl m (- n 1) smin smax))))))

;; Return individual with the given fitness from a population,
;; or NIL if no such element exists.
;;
(defun fit-individual (popl fitness ff)
  (if (= 0 (length popl))
    nil
    (if (= fitness (funcall (eval (read-from-string ff)) (car popl)))
      (car popl)
      (fit-individual (cdr popl) fitness ff))))

;; Return individuals with top m fitnesses / used for truncation selection.
;;
(defun best-individuals (m popl sfitnesses ff &optional (i 0) result)
  (progn
    (if (= i m)
      result
      (best-individuals m
                        popl
                        (cdr sfitnesses)
                        ff
                        (+ i 1)
                        (cons (fit-individual popl (car sfitnesses) ff)
                              result)))))

;; Given an oversized population (based on stable population size m),
;; repeatedly selects two random individuals to compete for survival,
;; dropping the individual with lower fitness.
;;
(defun battle (m popl ff)
  (if (= m (length popl))
    popl
    (let ((ff-fn (eval (read-from-string ff)))
          (a (nth (random (length popl)) popl))
          (b (nth (random (length popl)) popl)))
      (progn
        ;(format t "  DEBUG: ~S and ~S must compete for survival.~%" a b)
        (if (> (funcall ff-fn a) (funcall ff-fn b))
          (battle m (remove-first b popl) ff)      ; a >  b, so remove b
          (battle m (remove-first a popl) ff)))))) ; a <= b, so remove a

;; Truncation-selection (elitist) evolve algorithm.
;;
(defun evolve-trun (popl m n smin smax ff)
  (let* ((new-popl (append popl (reproduce popl m n smin smax)))
         (fitnesses (mapcar (eval (read-from-string ff)) new-popl))
         (sfitnesses (nsort #'max fitnesses)))
    (best-individuals m new-popl sfitnesses ff)))

;; EV-selection evolve algorithm.
;;
;; n offspring are produced, and then n times, two random individuals are
;; selected to "compete" and the lesser-fit individual is killed off.
;;
(defun evolve-battle (popl m n smin smax ff)
  (battle m (append popl (reproduce popl m n smin smax)) ff))

;; Evolve the population one generation.
;;
(defun evolve (popl m n smin smax ff)
  ;(evolve-trun popl m n smin smax ff))
  (evolve-battle popl m n smin smax ff))

;; Iterate through run-max generations of evolution, printing the population
;; at each evolution.
;;
(defun ev (smin smax m n ff run-max popl &optional (i 0))
  (if (> i run-max)
    nil
    (progn
      (format t "Step ~3S: ~S~%" i popl)
      (ev smin smax m n ff run-max (evolve popl m n smin smax ff) (+ i 1)))))

;; Print program information and start the evolution iterations.
;;
(defun start-ev (smin smax m n ff runs)
  (progn
    (format t "~%LEV. Christopher L. Simons, 2011~%~%")
    (format t "  Executing EV(~S, ~S) over solution space [~S, ~S];~%"
            m n smin smax)
    (format t "  for ~S generations, using fitness function ~S~%~%" runs ff)
    (ev smin smax m n ff runs (get-rand-popl smin smax m))))

;; Program's starting point.  Checks for the correct number of arguments,
;; and prints usage information or starts the program, depending on the result.
;;
(if (= 6 (length *args*))
  (start-ev (read-from-string (nth 0 *args*))
            (read-from-string (nth 1 *args*))
            (read-from-string (nth 2 *args*))
            (read-from-string (nth 3 *args*))
            (nth 4 *args*) ; don't interpret this one (fitness function)
            (read-from-string (nth 5 *args*)))
  (progn
    (format t "~%Expected 5 arguments, received ~S~%~%" (length *args*))
    (format t "usage: ./lev.lisp smin smax m n ff runs~%~%")
    (format t "       smin    solution space minimum~%")
    (format t "       smax    solution space maximum~%")
    (format t "          m    parent population size~%")
    (format t "          n    offspring population size~%")
    (format t "         ff    fitness function, in Lisp format~%")
    (format t "               (and in terms of x)~%")
    (format t "       runs    number of runs to execute, where each~%")
    (format t "               run comprises a single generation~%~%~%")
    (format t "  Using integers for smin and smax will result in a~%")
    (format t "  population of integer solution candidates, while using~%")
    (format t "  floating-point numbers will result in floating-point~%")
    (format t "  solution candidates.~%~%")))
