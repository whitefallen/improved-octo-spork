import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getAllRecipes, deleteRecipe } from '../../api/recipes';
import { Recipe } from '../../types';

const RecipeList = () => {
  const [recipes, setRecipes] = useState<Recipe[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    loadRecipes();
  }, []);

  const loadRecipes = async () => {
    try {
      const response = await getAllRecipes();
      setRecipes(response.data);
    } catch {
      setError('Failed to load recipes');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number, e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();
    if (!confirm('Delete this recipe?')) return;
    try {
      await deleteRecipe(id);
      setRecipes(recipes.filter((r) => r.id !== id));
    } catch {
      setError('Failed to delete recipe');
    }
  };

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="page">
      <div className="page-header">
        <h1>Recipes</h1>
        <button className="btn btn-primary" onClick={() => navigate('/recipes/new')}>
          + New Recipe
        </button>
      </div>
      {recipes.length === 0 ? (
        <p className="empty-state">No recipes yet. Create your first one!</p>
      ) : (
        <div className="card-list">
          {recipes.map((recipe) => (
            <div key={recipe.id} className="card" onClick={() => navigate(`/recipes/${recipe.id}`)}>
              <div className="card-content">
                <h3>{recipe.title}</h3>
                <p>{recipe.description}</p>
              </div>
              <div className="card-actions">
                <Link
                  to={`/recipes/${recipe.id}/edit`}
                  className="btn btn-secondary"
                  onClick={(e) => e.stopPropagation()}
                >
                  Edit
                </Link>
                <button
                  className="btn btn-danger"
                  onClick={(e) => handleDelete(recipe.id, e)}
                >
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

export default RecipeList;
