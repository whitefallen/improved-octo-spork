export interface BaseEntity {
  id: number;
  createdAt: string;
  updatedAt: string;
}

export interface Recipe extends BaseEntity {
  title: string;
  description: string;
  instructions: string;
  ingredients: string;
  frontendUrl?: string;
}

export interface Song extends BaseEntity {
  title: string;
  artist: string;
  album: string;
  genre: string;
  durationSeconds: number;
}

export interface Game extends BaseEntity {
  title: string;
  genre: string;
  platform: string;
  developer: string;
  releaseYear: number;
  description: string;
}
