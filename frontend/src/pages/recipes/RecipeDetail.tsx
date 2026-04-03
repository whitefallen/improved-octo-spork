import { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getRecipe, deleteRecipe } from '../../api/recipes';
import { Recipe } from '../../types';

const RecipeDetail = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [recipe, setRecipe] = useState<Recipe | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (id) {
      getRecipe(Number(id))
        .then((res) => setRecipe(res.data))
        .catch(() => setError('Failed to load recipe'))
        .finally(() => setLoading(false));
    }
  }, [id]);

  const handleDelete = async () => {
    if (!recipe || !confirm('Delete this recipe?')) return;
    try {
      await deleteRecipe(recipe.id);
      navigate('/recipes');
    } catch {
      setError('Failed to delete recipe');
    }
  };

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">{error}</div>;
  if (!recipe) return <div className="error">Recipe not found</div>;

  return (
    <div className="page">
      <div className="page-header">
        <h1>{recipe.title}</h1>
        <div className="header-actions">
          <Link to={`/recipes/${recipe.id}/edit`} className="btn btn-secondary">
            Edit
          </Link>
          <button className="btn btn-danger" onClick={handleDelete}>
            Delete
          </button>
          <Link to="/recipes" className="btn btn-outline">
            Back
          </Link>
        </div>
      </div>
      <div className="detail-card">
        <div className="detail-field">
          <label>Description</label>
          <p>{recipe.description}</p>
        </div>
        <div className="detail-field">
          <label>Ingredients</label>
          <p style={{ whiteSpace: 'pre-wrap' }}>{recipe.ingredients}</p>
        </div>
        <div className="detail-field">
          <label>Instructions</label>
          <p style={{ whiteSpace: 'pre-wrap' }}>{recipe.instructions}</p>
        </div>
        {recipe.frontendUrl && (
          <div className="detail-field">
            <label>URL</label>
            <a href={recipe.frontendUrl} target="_blank" rel="noopener noreferrer">
              {recipe.frontendUrl}
            </a>
          </div>
        )}
        <div className="detail-meta">
          <span>Created: {new Date(recipe.createdAt).toLocaleDateString()}</span>
          <span>Updated: {new Date(recipe.updatedAt).toLocaleDateString()}</span>
        </div>
      </div>
    </div>
  );
};

export default RecipeDetail;
