import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getAllGames, deleteGame } from '../../api/games';
import { Game } from '../../types';

const GameList = () => {
  const [games, setGames] = useState<Game[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    getAllGames()
      .then((res) => setGames(res.data))
      .catch(() => setError('Failed to load games'))
      .finally(() => setLoading(false));
  }, []);

  const handleDelete = async (id: number, e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();
    if (!confirm('Delete this game?')) return;
    try {
      await deleteGame(id);
      setGames(games.filter((g) => g.id !== id));
    } catch {
      setError('Failed to delete game');
    }
  };

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="page">
      <div className="page-header">
        <h1>Games</h1>
        <button className="btn btn-primary" onClick={() => navigate('/games/new')}>
          + New Game
        </button>
      </div>
      {games.length === 0 ? (
        <p className="empty-state">No games yet. Add your first one!</p>
      ) : (
        <div className="card-list">
          {games.map((game) => (
            <div key={game.id} className="card" onClick={() => navigate(`/games/${game.id}`)}>
              <div className="card-content">
                <h3>{game.title}</h3>
                <p>{game.developer} · {game.platform} · {game.releaseYear}</p>
                <span className="badge">{game.genre}</span>
              </div>
              <div className="card-actions">
                <Link
                  to={`/games/${game.id}/edit`}
                  className="btn btn-secondary"
                  onClick={(e) => e.stopPropagation()}
                >
                  Edit
                </Link>
                <button className="btn btn-danger" onClick={(e) => handleDelete(game.id, e)}>
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default GameList;
