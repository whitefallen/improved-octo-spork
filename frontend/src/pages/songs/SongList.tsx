import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getAllSongs, deleteSong } from '../../api/songs';
import { Song } from '../../types';

const SongList = () => {
  const [songs, setSongs] = useState<Song[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    getAllSongs()
      .then((res) => setSongs(res.data))
      .catch(() => setError('Failed to load songs'))
      .finally(() => setLoading(false));
  }, []);

  const handleDelete = async (id: number, e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();
    if (!confirm('Delete this song?')) return;
    try {
      await deleteSong(id);
      setSongs(songs.filter((s) => s.id !== id));
    } catch {
      setError('Failed to delete song');
    }
  };

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="page">
      <div className="page-header">
        <h1>Songs</h1>
        <button className="btn btn-primary" onClick={() => navigate('/songs/new')}>
          + New Song
        </button>
      </div>
      {songs.length === 0 ? (
        <p className="empty-state">No songs yet. Add your first one!</p>
      ) : (
        <div className="card-list">
          {songs.map((song) => (
            <div key={song.id} className="card" onClick={() => navigate(`/songs/${song.id}`)}>
              <div className="card-content">
                <h3>{song.title}</h3>
                <p>{song.artist} — {song.album}</p>
                <span className="badge">{song.genre}</span>
              </div>
              <div className="card-actions">
                <Link
                  to={`/songs/${song.id}/edit`}
                  className="btn btn-secondary"
                  onClick={(e) => e.stopPropagation()}
                >
                  Edit
                </Link>
                <button className="btn btn-danger" onClick={(e) => handleDelete(song.id, e)}>
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

export default SongList;
