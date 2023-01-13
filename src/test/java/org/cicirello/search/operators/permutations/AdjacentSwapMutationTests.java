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

package org.cicirello.search.operators.permutations;

import static org.junit.jupiter.api.Assertions.*;

import org.cicirello.permutations.Permutation;
import org.junit.jupiter.api.*;

/** JUnit test cases for AdjacentSwapMutation. */
public class AdjacentSwapMutationTests extends PermutationMutationValidator {

  @Test
  public void testAdjacentSwap() {
    AdjacentSwapMutation m = new AdjacentSwapMutation();
    undoTester(m);
    mutateTester(m);
    splitTester(m);
    // Verify mutations are adjacent swaps
    for (int n = 2; n <= 6; n++) {
      Permutation p = new Permutation(n);
      for (int t = 0; t < NUM_RAND_TESTS; t++) {
        Permutation mutant = new Permutation(p);
        m.mutate(mutant);
        int a, b;
        for (a = 0; a < p.length() && p.get(a) == mutant.get(a); a++)
          ;
        for (b = p.length() - 1; b >= 0 && p.get(b) == mutant.get(b); b--)
          ;
        assertEquals(a, b - 1);
        assertEquals(p.get(a), mutant.get(b));
        assertEquals(p.get(b), mutant.get(a));
      }
    }
    // Check distribution of random indexes
    for (int n = 2; n <= 6; n++) {
      boolean[] indexes = new boolean[n - 1];
      int numSamples = (n - 1) * 20;
      Permutation p = new Permutation(n);
      for (int i = 0; i < numSamples; i++) {
        Permutation mutant = new Permutation(p);
        m.mutate(mutant);
        int a;
        for (a = 0; a < p.length() && p.get(a) == mutant.get(a); a++)
          ;
        indexes[a] = true;
      }
      for (int i = 0; i < indexes.length; i++) {
        assertTrue(indexes[i]);
      }
    }
  }
}
