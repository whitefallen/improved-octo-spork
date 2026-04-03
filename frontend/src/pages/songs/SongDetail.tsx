import { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getSong, deleteSong } from '../../api/songs';
import { Song } from '../../types';

const SongDetail = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [song, setSong] = useState<Song | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (id) {
      getSong(Number(id))
        .then((res) => setSong(res.data))
        .catch(() => setError('Failed to load song'))
        .finally(() => setLoading(false));
    }
  }, [id]);

  const handleDelete = async () => {
    if (!song || !confirm('Delete this song?')) return;
    try {
      await deleteSong(song.id);
      navigate('/songs');
    } catch {
      setError('Failed to delete song');
    }
  };

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">{error}</div>;
  if (!song) return <div className="error">Song not found</div>;

  const minutes = Math.floor(song.durationSeconds / 60);
  const seconds = song.durationSeconds % 60;

  return (
    <div className="page">
      <div className="page-header">
        <h1>{song.title}</h1>
        <div className="header-actions">
          <Link to={`/songs/${song.id}/edit`} className="btn btn-secondary">Edit</Link>
          <button className="btn btn-danger" onClick={handleDelete}>Delete</button>
          <Link to="/songs" className="btn btn-outline">Back</Link>
        </div>
      </div>
      <div className="detail-card">
        <div className="detail-field"><label>Artist</label><p>{song.artist}</p></div>
        <div className="detail-field"><label>Album</label><p>{song.album}</p></div>
        <div className="detail-field"><label>Genre</label><p>{song.genre}</p></div>
        <div className="detail-field">
          <label>Duration</label>
          <p>{minutes}:{seconds.toString().padStart(2, '0')}</p>
        </div>
        <div className="detail-meta">
          <span>Created: {new Date(song.createdAt).toLocaleDateString()}</span>
          <span>Updated: {new Date(song.updatedAt).toLocaleDateString()}</span>
        </div>
      </div>
    </div>
  );
};

export default SongDetail;
