import { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getGame, deleteGame } from '../../api/games';
import { Game } from '../../types';

const GameDetail = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [game, setGame] = useState<Game | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (id) {
      getGame(Number(id))
        .then((res) => setGame(res.data))
        .catch(() => setError('Failed to load game'))
        .finally(() => setLoading(false));
    }
  }, [id]);

  const handleDelete = async () => {
    if (!game || !confirm('Delete this game?')) return;
    try {
      await deleteGame(game.id);
      navigate('/games');
    } catch {
      setError('Failed to delete game');
    }
  };

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">{error}</div>;
  if (!game) return <div className="error">Game not found</div>;

  return (
    <div className="page">
      <div className="page-header">
        <h1>{game.title}</h1>
        <div className="header-actions">
          <Link to={`/games/${game.id}/edit`} className="btn btn-secondary">Edit</Link>
          <button className="btn btn-danger" onClick={handleDelete}>Delete</button>
          <Link to="/games" className="btn btn-outline">Back</Link>
        </div>
      </div>
      <div className="detail-card">
        <div className="detail-field"><label>Developer</label><p>{game.developer}</p></div>
        <div className="detail-field"><label>Platform</label><p>{game.platform}</p></div>
        <div className="detail-field"><label>Genre</label><p>{game.genre}</p></div>
        <div className="detail-field"><label>Release Year</label><p>{game.releaseYear}</p></div>
        <div className="detail-field"><label>Description</label><p>{game.description}</p></div>
        <div className="detail-meta">
          <span>Created: {new Date(game.createdAt).toLocaleDateString()}</span>
          <span>Updated: {new Date(game.updatedAt).toLocaleDateString()}</span>
        </div>
      </div>
    </div>
  );
};

export default GameDetail;
