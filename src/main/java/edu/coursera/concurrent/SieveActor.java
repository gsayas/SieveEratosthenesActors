package edu.coursera.concurrent;

import edu.rice.pcdp.Actor;

import java.util.Arrays;
import java.util.List;

import static edu.rice.pcdp.PCDP.finish;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;

/**
 * An actor-based implementation of the Sieve of Eratosthenes.
 *
 * TODO Fill in the empty SieveActorActor actor class below and use it from
 * countPrimes to determin the number of primes <= limit.
 */
public final class SieveActor extends Sieve {
    /**
     * {@inheritDoc}
     *
     * TODO Use the SieveActorActor class to calculate the number of primes <=
     * limit in parallel. You might consider how you can model the Sieve of
     * Eratosthenes as a pipeline of actors, each corresponding to a single
     * prime number.
     */

    private int numPrimes = 0;
    private int[] candidates; //odd numbers greater than 2
    private int limit;

    @Override
    public int countPrimes(final int limit) {

        this.limit = limit;

        if(limit <= 2)
            return trivialCases();

        numPrimes = 1;
        initializeCandidates();

        SieveActorActor sieveActor = new SieveActorActor();
        finish(() -> {
            sieveActor.send(candidates);
        });

        SieveActorActor loopActor = sieveActor;
        while(loopActor != null){
            numPrimes++;
            loopActor = loopActor.nextActor;
        }

        return numPrimes;
    }

    private void initializeCandidates(){
        int candidatesSize = (int)floor(limit/2);
        candidates = new int[candidatesSize];
        int i = 0;

        for(int oddIndex = 3; i<=candidatesSize-1 && oddIndex<=limit; oddIndex+=2){
            candidates[i] = oddIndex;
            //System.out.print(candidates[i] + ", ");
            i++;
        }
    }

    private int trivialCases() {
        return limit < 2 ? 0 : 1;
    }

    /**
     * An actor class that helps implement the Sieve of Eratosthenes in
     * parallel.
     */
    public static final class SieveActorActor extends Actor {
        /**
         * Process a single message sent to this actor.
         *
         * TODO complete this method.
         *
         * @param msg Received message
         */
        private SieveActorActor nextActor;

        @Override
        public void process(final Object msg) {
            int[] candidates = (int[]) msg;
            int[] nextCandidates = Arrays.stream(candidates).parallel().filter(i -> i%candidates[0] != 0).toArray();

            if(nextCandidates.length > 0){
                nextActor = new SieveActorActor();
                nextActor.send(nextCandidates);
            }
        }
    }
}