import { Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import RecipeList from './pages/recipes/RecipeList';
import RecipeDetail from './pages/recipes/RecipeDetail';
import RecipeForm from './pages/recipes/RecipeForm';
import SongList from './pages/songs/SongList';
import SongDetail from './pages/songs/SongDetail';
import SongForm from './pages/songs/SongForm';
import GameList from './pages/games/GameList';
import GameDetail from './pages/games/GameDetail';
import GameForm from './pages/games/GameForm';

function App() {
  return (
    <div className="app">
      <Navbar />
      <main className="main-content">
        <Routes>
          <Route path="/" element={<Navigate to="/recipes" replace />} />
          <Route path="/recipes" element={<RecipeList />} />
          <Route path="/recipes/new" element={<RecipeForm />} />
          <Route path="/recipes/:id" element={<RecipeDetail />} />
          <Route path="/recipes/:id/edit" element={<RecipeForm />} />
          <Route path="/songs" element={<SongList />} />
          <Route path="/songs/new" element={<SongForm />} />
          <Route path="/songs/:id" element={<SongDetail />} />
          <Route path="/songs/:id/edit" element={<SongForm />} />
          <Route path="/games" element={<GameList />} />
          <Route path="/games/new" element={<GameForm />} />
          <Route path="/games/:id" element={<GameDetail />} />
          <Route path="/games/:id/edit" element={<GameForm />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;
