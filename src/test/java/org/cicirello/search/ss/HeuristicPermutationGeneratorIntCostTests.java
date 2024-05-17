/*
 * Chips-n-Salsa: A library of parallel self-adaptive local search algorithms.
 * Copyright (C) 2002-2024 Vincent A. Cicirello
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

package org.cicirello.search.ss;

import static org.junit.jupiter.api.Assertions.*;

import org.cicirello.permutations.Permutation;
import org.cicirello.search.ProgressTracker;
import org.cicirello.search.SolutionCostPair;
import org.junit.jupiter.api.*;

/** JUnit tests for the HeuristicPermutationGenerator class for problems with int-valued costs. */
public class HeuristicPermutationGeneratorIntCostTests extends SharedTestStochasticSampler {

  @Test
  public void testWithIntCosts() {
    for (int n = 0; n < 5; n++) {
      IntProblem problem = new IntProblem();
      IntHeuristic h = new IntHeuristic(problem, n);
      HeuristicPermutationGenerator ch =
          HeuristicPermutationGenerator.createHeuristicPermutationGenerator(h);
      assertEquals(0, ch.getTotalRunLength());
      assertTrue(problem == ch.getProblem());
      ProgressTracker<Permutation> tracker = ch.getProgressTracker();
      SolutionCostPair<Permutation> solution = ch.optimize();
      assertEquals(1, ch.getTotalRunLength());
      assertEquals((n + 1) * n / 2, solution.getCost());
      assertEquals((n + 1) * n / 2, tracker.getCost());
      Permutation p = solution.getSolution();
      assertEquals(n, p.length());
      int evenStart = (n % 2 == 0) ? n - 2 : n - 1;
      int oddStart = (n % 2 == 0) ? n - 1 : n - 2;
      int i = 0;
      for (int expected = evenStart; expected >= 0 && i < n; expected -= 2, i++) {
        assertEquals(expected, p.get(i));
      }
      for (int expected = oddStart; expected > 0 && i < n; expected -= 2, i++) {
        assertEquals(expected, p.get(i));
      }
      solution = ch.optimize();
      assertEquals(2, ch.getTotalRunLength());
      assertEquals((n + 1) * n / 2, solution.getCost());
      assertEquals((n + 1) * n / 2, tracker.getCost());
      tracker = new ProgressTracker<Permutation>();
      ch.setProgressTracker(tracker);
      assertEquals(tracker, ch.getProgressTracker());
      ch.setProgressTracker(null);
      assertEquals(tracker, ch.getProgressTracker());
    }
  }

  @Test
  public void testHeuristicNullIncremental() {
    for (int n = 0; n < 5; n++) {
      IntProblem problem = new IntProblem();
      IntHeuristicNullIncremental h = new IntHeuristicNullIncremental(problem, n);
      HeuristicPermutationGenerator ch =
          HeuristicPermutationGenerator.createHeuristicPermutationGenerator(h);
      assertEquals(0, ch.getTotalRunLength());
      assertTrue(problem == ch.getProblem());
      ProgressTracker<Permutation> tracker = ch.getProgressTracker();
      SolutionCostPair<Permutation> solution = ch.optimize();
      assertEquals(1, ch.getTotalRunLength());
      assertEquals((n + 1) * n / 2, solution.getCost());
      assertEquals((n + 1) * n / 2, tracker.getCost());
      Permutation p = solution.getSolution();
      assertEquals(n, p.length());
      int evenStart = (n % 2 == 0) ? n - 2 : n - 1;
      int oddStart = (n % 2 == 0) ? n - 1 : n - 2;
      int i = 0;
      for (int expected = evenStart; expected >= 0 && i < n; expected -= 2, i++) {
        assertEquals(expected, p.get(i));
      }
      for (int expected = oddStart; expected > 0 && i < n; expected -= 2, i++) {
        assertEquals(expected, p.get(i));
      }
      solution = ch.optimize();
      assertEquals(2, ch.getTotalRunLength());
      assertEquals((n + 1) * n / 2, solution.getCost());
      assertEquals((n + 1) * n / 2, tracker.getCost());
      tracker = new ProgressTracker<Permutation>();
      ch.setProgressTracker(tracker);
      assertEquals(tracker, ch.getProgressTracker());
      ch.setProgressTracker(null);
      assertEquals(tracker, ch.getProgressTracker());
    }
  }

  @Test
  public void testWithIntCostsWithProgressTracker() {
    for (int n = 0; n < 5; n++) {
      ProgressTracker<Permutation> originalTracker = new ProgressTracker<Permutation>();
      IntProblem problem = new IntProblem();
      IntHeuristic h = new IntHeuristic(problem, n);
      HeuristicPermutationGenerator ch =
          HeuristicPermutationGenerator.createHeuristicPermutationGenerator(h, originalTracker);
      assertEquals(0, ch.getTotalRunLength());
      assertTrue(problem == ch.getProblem());
      ProgressTracker<Permutation> tracker = ch.getProgressTracker();
      assertTrue(originalTracker == tracker);
      SolutionCostPair<Permutation> solution = ch.optimize();
      assertEquals(1, ch.getTotalRunLength());
      assertEquals((n + 1) * n / 2, solution.getCost());
      assertEquals((n + 1) * n / 2, tracker.getCost());
      Permutation p = solution.getSolution();
      assertEquals(n, p.length());
      int evenStart = (n % 2 == 0) ? n - 2 : n - 1;
      int oddStart = (n % 2 == 0) ? n - 1 : n - 2;
      int i = 0;
      for (int expected = evenStart; expected >= 0 && i < n; expected -= 2, i++) {
        assertEquals(expected, p.get(i));
      }
      for (int expected = oddStart; expected > 0 && i < n; expected -= 2, i++) {
        assertEquals(expected, p.get(i));
      }
      tracker = new ProgressTracker<Permutation>();
      ch.setProgressTracker(tracker);
      assertTrue(tracker == ch.getProgressTracker());
    }
  }

  @Test
  public void testWithIntCostsSplit() {
    for (int n = 0; n < 5; n++) {
      IntProblem problem = new IntProblem();
      IntHeuristic h = new IntHeuristic(problem, n);
      HeuristicPermutationGenerator chOriginal =
          HeuristicPermutationGenerator.createHeuristicPermutationGenerator(h);
      HeuristicPermutationGenerator ch = chOriginal.split();
      assertEquals(0, ch.getTotalRunLength());
      assertTrue(problem == ch.getProblem());
      ProgressTracker<Permutation> tracker = ch.getProgressTracker();
      SolutionCostPair<Permutation> solution = ch.optimize();
      assertEquals(1, ch.getTotalRunLength());
      assertEquals((n + 1) * n / 2, solution.getCost());
      assertEquals((n + 1) * n / 2, tracker.getCost());
      Permutation p = solution.getSolution();
      assertEquals(n, p.length());
      int evenStart = (n % 2 == 0) ? n - 2 : n - 1;
      int oddStart = (n % 2 == 0) ? n - 1 : n - 2;
      int i = 0;
      for (int expected = evenStart; expected >= 0 && i < n; expected -= 2, i++) {
        assertEquals(expected, p.get(i));
      }
      for (int expected = oddStart; expected > 0 && i < n; expected -= 2, i++) {
        assertEquals(expected, p.get(i));
      }
      tracker = new ProgressTracker<Permutation>();
      ch.setProgressTracker(tracker);
      assertTrue(tracker == ch.getProgressTracker());
    }
  }
}
