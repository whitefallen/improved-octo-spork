import { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getSong, createSong, updateSong } from '../../api/songs';

const SongForm = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const isEdit = Boolean(id);

  const [formData, setFormData] = useState({
    title: '',
    artist: '',
    album: '',
    genre: '',
    durationSeconds: 0,
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (isEdit && id) {
      setLoading(true);
      getSong(Number(id))
        .then((res) => {
          const s = res.data;
          setFormData({
            title: s.title,
            artist: s.artist,
            album: s.album,
            genre: s.genre,
            durationSeconds: s.durationSeconds,
          });
        })
        .catch(() => setError('Failed to load song'))
        .finally(() => setLoading(false));
    }
  }, [id, isEdit]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.type === 'number' ? Number(e.target.value) : e.target.value;
    setFormData({ ...formData, [e.target.name]: value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      if (isEdit && id) {
        await updateSong(Number(id), formData);
        navigate(`/songs/${id}`);
      } else {
        const res = await createSong(formData);
        navigate(`/songs/${res.data.id}`);
      }
    } catch {
      setError('Failed to save song');
      setLoading(false);
    }
  };

  if (loading && isEdit) return <div className="loading">Loading...</div>;

  return (
    <div className="page">
      <div className="page-header">
        <h1>{isEdit ? 'Edit Song' : 'New Song'}</h1>
        <Link to={isEdit ? `/songs/${id}` : '/songs'} className="btn btn-outline">Cancel</Link>
      </div>
      {error && <div className="error">{error}</div>}
      <form className="form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="title">Title *</label>
          <input id="title" name="title" type="text" value={formData.title} onChange={handleChange} required placeholder="Song title" />
        </div>
        <div className="form-group">
          <label htmlFor="artist">Artist *</label>
          <input id="artist" name="artist" type="text" value={formData.artist} onChange={handleChange} required placeholder="Artist name" />
        </div>
        <div className="form-group">
          <label htmlFor="album">Album *</label>
          <input id="album" name="album" type="text" value={formData.album} onChange={handleChange} required placeholder="Album name" />
        </div>
        <div className="form-group">
          <label htmlFor="genre">Genre *</label>
          <input id="genre" name="genre" type="text" value={formData.genre} onChange={handleChange} required placeholder="Genre" />
        </div>
        <div className="form-group">
          <label htmlFor="durationSeconds">Duration (seconds) *</label>
          <input id="durationSeconds" name="durationSeconds" type="number" value={formData.durationSeconds} onChange={handleChange} required min="1" />
        </div>
        <div className="form-actions">
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Saving...' : isEdit ? 'Update Song' : 'Create Song'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default SongForm;
