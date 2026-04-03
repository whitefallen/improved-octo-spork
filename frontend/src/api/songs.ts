import client from './client';
import { Song } from '../types';

export const getAllSongs = () => client.get<Song[]>('/songs');
export const getSong = (id: number) => client.get<Song>(`/songs/${id}`);
export const createSong = (data: Omit<Song, 'id' | 'createdAt' | 'updatedAt'>) =>
  client.post<Song>('/songs', data);
export const updateSong = (id: number, data: Omit<Song, 'id' | 'createdAt' | 'updatedAt'>) =>
  client.put<Song>(`/songs/${id}`, data);
export const deleteSong = (id: number) => client.delete(`/songs/${id}`);
