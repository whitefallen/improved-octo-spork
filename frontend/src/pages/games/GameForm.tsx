import { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getGame, createGame, updateGame } from '../../api/games';

const GameForm = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const isEdit = Boolean(id);

  const [formData, setFormData] = useState({
    title: '',
    genre: '',
    platform: '',
    developer: '',
    releaseYear: new Date().getFullYear(),
    description: '',
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (isEdit && id) {
      setLoading(true);
      getGame(Number(id))
        .then((res) => {
          const g = res.data;
          setFormData({
            title: g.title,
            genre: g.genre,
            platform: g.platform,
            developer: g.developer,
            releaseYear: g.releaseYear,
            description: g.description,
          });
        })
        .catch(() => setError('Failed to load game'))
        .finally(() => setLoading(false));
    }
  }, [id, isEdit]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const value = e.target.type === 'number' ? Number(e.target.value) : e.target.value;
    setFormData({ ...formData, [e.target.name]: value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      if (isEdit && id) {
        await updateGame(Number(id), formData);
        navigate(`/games/${id}`);
      } else {
        const res = await createGame(formData);
        navigate(`/games/${res.data.id}`);
      }
    } catch {
      setError('Failed to save game');
      setLoading(false);
    }
  };

  if (loading && isEdit) return <div className="loading">Loading...</div>;

  return (
    <div className="page">
      <div className="page-header">
        <h1>{isEdit ? 'Edit Game' : 'New Game'}</h1>
        <Link to={isEdit ? `/games/${id}` : '/games'} className="btn btn-outline">Cancel</Link>
      </div>
      {error && <div className="error">{error}</div>}
      <form className="form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="title">Title *</label>
          <input id="title" name="title" type="text" value={formData.title} onChange={handleChange} required placeholder="Game title" />
        </div>
        <div className="form-group">
          <label htmlFor="developer">Developer *</label>
          <input id="developer" name="developer" type="text" value={formData.developer} onChange={handleChange} required placeholder="Developer name" />
        </div>
        <div className="form-group">
          <label htmlFor="platform">Platform *</label>
          <input id="platform" name="platform" type="text" value={formData.platform} onChange={handleChange} required placeholder="e.g. PC, PlayStation 5" />
        </div>
        <div className="form-group">
          <label htmlFor="genre">Genre *</label>
          <input id="genre" name="genre" type="text" value={formData.genre} onChange={handleChange} required placeholder="e.g. RPG, Action" />
        </div>
        <div className="form-group">
          <label htmlFor="releaseYear">Release Year *</label>
          <input id="releaseYear" name="releaseYear" type="number" value={formData.releaseYear} onChange={handleChange} required min="1970" max="2100" />
        </div>
        <div className="form-group">
          <label htmlFor="description">Description *</label>
          <textarea id="description" name="description" value={formData.description} onChange={handleChange} required placeholder="Game description" rows={4} />
        </div>
        <div className="form-actions">
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Saving...' : isEdit ? 'Update Game' : 'Create Game'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default GameForm;
