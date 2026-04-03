import client from './client';
import { Game } from '../types';

export const getAllGames = () => client.get<Game[]>('/games');
export const getGame = (id: number) => client.get<Game>(`/games/${id}`);
export const createGame = (data: Omit<Game, 'id' | 'createdAt' | 'updatedAt'>) =>
  client.post<Game>('/games', data);
export const updateGame = (id: number, data: Omit<Game, 'id' | 'createdAt' | 'updatedAt'>) =>
  client.put<Game>(`/games/${id}`, data);
export const deleteGame = (id: number) => client.delete(`/games/${id}`);
