/*
 * Chips-n-Salsa: A library of parallel self-adaptive local search algorithms.
 * Copyright (C) 2002-2022 Vincent A. Cicirello
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

package org.cicirello.search.evo;

import org.cicirello.util.Copyable;
import org.cicirello.search.ProgressTracker;
import org.cicirello.search.problems.Problem;
import org.cicirello.search.operators.Initializer;
import org.cicirello.search.operators.MutationOperator;
import org.cicirello.math.rand.RandomVariates;

/**
 * <p>This class implements an evolutionary algorithm (EA) with a generational
 * model, such as is commonly used in genetic algorithms, where a
 * population of children are formed by applying mutation to
 * members of the parent population, and where the children replace the 
 * parents in the next generation. The EA implemented by this class does not
 * use crossover.</p>
 *
 * <p>The mutation and selection operators are completely configurable
 * by passing instances of classes that implement the
 * {@link MutationOperator}, and {@link SelectionOperator} classes to one of the
 * constructors.</p>
 *
 * <p>See the {@link GenerationalEvolutionaryAlgorithm} class for a generational EA
 * with both crossover and mutation, with the common genetic algorithm style generation,
 * such that the next generation is formed from children that replace the parents,
 * and such that children can be the result of crossover alone, mutation alone, both
 * crossover and mutation, or simply identical copies of parents. The library also includes
 * a variation of this generation structure in the class {@link GenerationalNANDOperatorsEvolutionaryAlgorithm},
 * where crossover and mutation are treated as mutually exclusive operators such that a child
 * in a generation may be the result of crossover, or mutation, or an identical copy, but never
 * the result of both crossover and mutation.</p>
 *
 * @param <T> The type of object under optimization.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, 
 * <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public class GenerationalMutationOnlyEvolutionaryAlgorithm<T extends Copyable<T>> extends AbstractEvolutionaryAlgorithm<T> {
	
	private final MutationOperator<T> mutation;
	private final double M;
	
	/**
	 * Constructs and initializes the evolutionary algorithm with mutation only. This constructor supports fitness functions
	 * with fitnesses of type double, the {@link FitnessFunction.Double} interface.
	 *
	 * @param n The population size.
	 * @param mutation The mutation operator.
	 * @param mutationRate The probability that a member of the population is mutated once during a generation. Note that
	 *     this is not a per-bit rate since this class is generalized to evolution of any {@link Copyable} object type.
	 *     For {@link org.cicirello.search.representations.BitVector} optimization and traditional genetic algorithm 
	 *     interpretation of mutation rate, configure
	 *     your mutation operator with the per-bit mutation rate, and then pass 1.0 for this parameter.
	 * @param initializer An initializer for generating random initial population members.
	 * @param f The fitness function.
	 * @param selection The selection operator.
	 * @param tracker A ProgressTracker.
	 *
	 * @throws IllegalArgumentException if n is less than 1.
	 * @throws IllegalArgumentException if mutationRate is less than 0.
	 * @throws NullPointerException if any of mutation, initializer, f, selection, or tracker are null.
	 */
	public GenerationalMutationOnlyEvolutionaryAlgorithm(int n, MutationOperator<T> mutation, double mutationRate, Initializer<T> initializer, FitnessFunction.Double<T> f, SelectionOperator selection, ProgressTracker<T> tracker) {
		this(new BasePopulation.Double<T>(n, initializer, f, selection, tracker, 0), f.getProblem(), mutation, mutationRate);
	}
	
	/**
	 * Constructs and initializes the evolutionary algorithm with mutation only. This constructor supports fitness functions
	 * with fitnesses of type int, the {@link FitnessFunction.Integer} interface.
	 *
	 * @param n The population size.
	 * @param mutation The mutation operator.
	 * @param mutationRate The probability that a member of the population is mutated once during a generation. Note that
	 *     this is not a per-bit rate since this class is generalized to evolution of any {@link Copyable} object type.
	 *     For {@link org.cicirello.search.representations.BitVector} optimization and traditional genetic algorithm 
	 *     interpretation of mutation rate, configure
	 *     your mutation operator with the per-bit mutation rate, and then pass 1.0 for this parameter.
	 * @param initializer An initializer for generating random initial population members.
	 * @param f The fitness function.
	 * @param selection The selection operator.
	 * @param tracker A ProgressTracker.
	 *
	 * @throws IllegalArgumentException if n is less than 1.
	 * @throws IllegalArgumentException if mutationRate is less than 0.
	 * @throws NullPointerException if any of mutation, initializer, f, selection, or tracker are null.
	 */
	public GenerationalMutationOnlyEvolutionaryAlgorithm(int n, MutationOperator<T> mutation, double mutationRate, Initializer<T> initializer, FitnessFunction.Integer<T> f, SelectionOperator selection, ProgressTracker<T> tracker) {
		this(new BasePopulation.Integer<T>(n, initializer, f, selection, tracker, 0), f.getProblem(), mutation, mutationRate);
	}
	
	/**
	 * Constructs and initializes the evolutionary algorithm with mutation only. This constructor supports fitness functions
	 * with fitnesses of type double, the {@link FitnessFunction.Double} interface.
	 *
	 * @param n The population size.
	 * @param mutation The mutation operator.
	 * @param mutationRate The probability that a member of the population is mutated once during a generation. Note that
	 *     this is not a per-bit rate since this class is generalized to evolution of any {@link Copyable} object type.
	 *     For {@link org.cicirello.search.representations.BitVector} optimization and traditional genetic algorithm 
	 *     interpretation of mutation rate, configure
	 *     your mutation operator with the per-bit mutation rate, and then pass 1.0 for this parameter.
	 * @param initializer An initializer for generating random initial population members.
	 * @param f The fitness function.
	 * @param selection The selection operator.
	 *
	 * @throws IllegalArgumentException if n is less than 1.
	 * @throws IllegalArgumentException if mutationRate is less than 0.
	 * @throws NullPointerException if any of mutation, initializer, f, or selection are null.
	 */
	public GenerationalMutationOnlyEvolutionaryAlgorithm(int n, MutationOperator<T> mutation, double mutationRate, Initializer<T> initializer, FitnessFunction.Double<T> f, SelectionOperator selection) {
		this(new BasePopulation.Double<T>(n, initializer, f, selection, new ProgressTracker<T>(), 0), f.getProblem(), mutation, mutationRate);
	}
	
	/**
	 * Constructs and initializes the evolutionary algorithm with mutation only. This constructor supports fitness functions
	 * with fitnesses of type int, the {@link FitnessFunction.Integer} interface.
	 *
	 * @param n The population size.
	 * @param mutation The mutation operator.
	 * @param mutationRate The probability that a member of the population is mutated once during a generation. Note that
	 *     this is not a per-bit rate since this class is generalized to evolution of any {@link Copyable} object type.
	 *     For {@link org.cicirello.search.representations.BitVector} optimization and traditional genetic algorithm 
	 *     interpretation of mutation rate, configure
	 *     your mutation operator with the per-bit mutation rate, and then pass 1.0 for this parameter.
	 * @param initializer An initializer for generating random initial population members.
	 * @param f The fitness function.
	 * @param selection The selection operator.
	 *
	 * @throws IllegalArgumentException if n is less than 1.
	 * @throws IllegalArgumentException if mutationRate is less than 0.
	 * @throws NullPointerException if any of mutation, initializer, f, or selection are null.
	 */
	public GenerationalMutationOnlyEvolutionaryAlgorithm(int n, MutationOperator<T> mutation, double mutationRate, Initializer<T> initializer, FitnessFunction.Integer<T> f, SelectionOperator selection) {
		this(new BasePopulation.Integer<T>(n, initializer, f, selection, new ProgressTracker<T>(), 0), f.getProblem(), mutation, mutationRate);
	}
	
	
	// Internal Constructors
	
	/*
	 * Internal helper constructor for Mutation-Only EAs.
	 */
	private GenerationalMutationOnlyEvolutionaryAlgorithm(Population<T> pop, Problem<T> problem, MutationOperator<T> mutation, double mutationRate) {
		super(pop, problem);
		if (mutation == null) {
			throw new NullPointerException("mutation must be non-null");
		}
		if (mutationRate < 0.0) {
			throw new IllegalArgumentException("mutationRate must not be negative");
		}
		this.mutation = mutation;
		
		M = mutationRate < 1.0 ? mutationRate : 1.0;
	}
	
	/*
	 * Internal constructor for use by split method.
	 * package private so subclasses in same package can use it for initialization for their own split methods.
	 */
	GenerationalMutationOnlyEvolutionaryAlgorithm(GenerationalMutationOnlyEvolutionaryAlgorithm<T> other) {
		super(other);
		
		// Must be split
		mutation = other.mutation.split();
		
		// Threadsafe so just copy reference or values
		M = other.M;
	}
	
	@Override
	public GenerationalMutationOnlyEvolutionaryAlgorithm<T> split() {
		return new GenerationalMutationOnlyEvolutionaryAlgorithm<T>(this);
	}
	
	@Override
	final int oneGeneration(Population<T> pop) {
		pop.select();
		final int count = 
			M == 1.0 ?
				pop.mutableSize()
				: RandomVariates.nextBinomial(pop.mutableSize(), M);
		// Since select() randomizes ordering, just use a binomial
		// to get count of how many to mutate and mutate the first count individuals.
		// Although if M is 1.0 just mutate them all without computing the binomial.
		for (int j = 0; j < count; j++) {
			mutation.mutate(pop.get(j));
			pop.updateFitness(j);
		}
		pop.replace();
		return count;
	}
}
