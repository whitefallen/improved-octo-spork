import { Link, useLocation } from 'react-router-dom';

const Navbar = () => {
  const location = useLocation();

  const isActive = (path: string) => location.pathname.startsWith(path);

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <Link to="/">MyApp</Link>
      </div>
      <div className="navbar-links">
        <Link to="/recipes" className={isActive('/recipes') ? 'active' : ''}>
          Recipes
        </Link>
        <Link to="/songs" className={isActive('/songs') ? 'active' : ''}>
          Songs
        </Link>
        <Link to="/games" className={isActive('/games') ? 'active' : ''}>
          Games
        </Link>
      </div>
    </nav>
  );
};

export default Navbar;
