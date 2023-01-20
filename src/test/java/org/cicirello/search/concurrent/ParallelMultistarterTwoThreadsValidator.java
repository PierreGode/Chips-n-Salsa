/*
 * Chips-n-Salsa: A library of parallel self-adaptive local search algorithms.
 * Copyright (C) 2002-2023 Vincent A. Cicirello
 *
 * This file is part of Chips-n-Salsa (https://chips-n-salsa.cicirello.org/).
 *
 * Chips-n-Salsa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Chips-n-Salsa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.cicirello.search.concurrent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.SplittableRandom;
import org.cicirello.search.ProgressTracker;
import org.cicirello.search.ReoptimizableMetaheuristic;
import org.cicirello.search.problems.OptimizationProblem;
import org.junit.jupiter.api.*;

/** Test validation common to multiple test classes for testing parallel multistarters. */
public class ParallelMultistarterTwoThreadsValidator extends ParallelMultistarterValidator {

  static class TestRestartedMetaheuristic extends AbstractTestRestartedMetaheuristic
      implements ReoptimizableMetaheuristic<TestObject> {

    private final OptimizationProblem<TestObject> problem;
    ;

    public TestRestartedMetaheuristic() {
      this(new TestProblem());
    }

    public TestRestartedMetaheuristic(TestProblem p) {
      super();
      problem = p;
    }

    public TestRestartedMetaheuristic(int stopAtEval, int findBestAtEval) {
      this(stopAtEval, findBestAtEval, new SplittableRandom(42), new ProgressTracker<TestObject>());
    }

    public TestRestartedMetaheuristic(int stopAtEval, int findBestAtEval, TestProblem p) {
      this(
          stopAtEval,
          findBestAtEval,
          new SplittableRandom(42),
          new ProgressTracker<TestObject>(),
          p);
    }

    public TestRestartedMetaheuristic(
        int stopAtEval,
        int findBestAtEval,
        SplittableRandom rand,
        ProgressTracker<TestObject> tracker) {
      this(stopAtEval, findBestAtEval, rand, tracker, new TestProblem());
    }

    public TestRestartedMetaheuristic(
        int stopAtEval,
        int findBestAtEval,
        SplittableRandom rand,
        ProgressTracker<TestObject> tracker,
        TestProblem p) {
      super(stopAtEval, findBestAtEval, rand, tracker);
      problem = p;
    }

    public TestRestartedMetaheuristic(TestRestartedMetaheuristic other) {
      super(other);
      problem = other.problem;
    }

    @Override
    public TestRestartedMetaheuristic split() {
      return new TestRestartedMetaheuristic(this);
    }

    @Override
    public OptimizationProblem<TestObject> getProblem() {
      // not used by tests.
      return problem;
    }
  }
}
