import { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getRecipe, createRecipe, updateRecipe } from '../../api/recipes';

const RecipeForm = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const isEdit = Boolean(id);

  const [formData, setFormData] = useState({
    title: '',
    description: '',
    instructions: '',
    ingredients: '',
    frontendUrl: '',
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (isEdit && id) {
      setLoading(true);
      getRecipe(Number(id))
        .then((res) => {
          const r = res.data;
          setFormData({
            title: r.title,
            description: r.description,
            instructions: r.instructions,
            ingredients: r.ingredients,
            frontendUrl: r.frontendUrl || '',
          });
        })
        .catch(() => setError('Failed to load recipe'))
        .finally(() => setLoading(false));
    }
  }, [id, isEdit]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      const data = {
        ...formData,
        frontendUrl: formData.frontendUrl || undefined,
      };
      if (isEdit && id) {
        await updateRecipe(Number(id), data);
        navigate(`/recipes/${id}`);
      } else {
        const res = await createRecipe(data);
        navigate(`/recipes/${res.data.id}`);
      }
    } catch {
      setError('Failed to save recipe');
      setLoading(false);
    }
  };

  if (loading && isEdit) return <div className="loading">Loading...</div>;

  return (
    <div className="page">
      <div className="page-header">
        <h1>{isEdit ? 'Edit Recipe' : 'New Recipe'}</h1>
        <Link to={isEdit ? `/recipes/${id}` : '/recipes'} className="btn btn-outline">
          Cancel
        </Link>
      </div>
      {error && <div className="error">{error}</div>}
      <form className="form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="title">Title *</label>
          <input
            id="title"
            name="title"
            type="text"
            value={formData.title}
            onChange={handleChange}
            required
            placeholder="Recipe title"
          />
        </div>
        <div className="form-group">
          <label htmlFor="description">Description *</label>
          <textarea
            id="description"
            name="description"
            value={formData.description}
            onChange={handleChange}
            required
            placeholder="Brief description"
            rows={3}
          />
        </div>
        <div className="form-group">
          <label htmlFor="ingredients">Ingredients *</label>
          <textarea
            id="ingredients"
            name="ingredients"
            value={formData.ingredients}
            onChange={handleChange}
            required
            placeholder="List ingredients..."
            rows={5}
          />
        </div>
        <div className="form-group">
          <label htmlFor="instructions">Instructions *</label>
          <textarea
            id="instructions"
            name="instructions"
            value={formData.instructions}
            onChange={handleChange}
            required
            placeholder="Step-by-step instructions..."
            rows={6}
          />
        </div>
        <div className="form-group">
          <label htmlFor="frontendUrl">URL (optional)</label>
          <input
            id="frontendUrl"
            name="frontendUrl"
            type="url"
            value={formData.frontendUrl}
            onChange={handleChange}
            placeholder="https://example.com/recipe"
          />
        </div>
        <div className="form-actions">
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Saving...' : isEdit ? 'Update Recipe' : 'Create Recipe'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default RecipeForm;
