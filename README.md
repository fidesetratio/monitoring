 For <job restartable="true/false" />, the default is true. 

We can restart any non-COMPLETED/ABANDONED job instance. If set to false, a failed job instance wouldn't restart.

For <tasklet allow-start-if-complete="true/false">, the default is false. 

E.g. a job has three steps (step1, step2, step3) and  allow-start-if-complete is set to false. 
A first execution runs and fails during step3. On a restart, Spring Batch will go directly to step3 and try to finish the execution. 

Now if allow-start-if-complete=true on step1 tasklet. On a restart (after a failure on step3), Spring Batch would execute step1, skip step2, and execute step3. 
