import client from './client';
import { Recipe } from '../types';

export const getAllRecipes = () => client.get<Recipe[]>('/recipes');
export const getRecipe = (id: number) => client.get<Recipe>(`/recipes/${id}`);
export const createRecipe = (data: Omit<Recipe, 'id' | 'createdAt' | 'updatedAt'>) =>
  client.post<Recipe>('/recipes', data);
export const updateRecipe = (id: number, data: Omit<Recipe, 'id' | 'createdAt' | 'updatedAt'>) =>
  client.put<Recipe>(`/recipes/${id}`, data);
export const deleteRecipe = (id: number) => client.delete(`/recipes/${id}`);
