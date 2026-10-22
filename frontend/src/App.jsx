
import { useEffect, useState } from "react";
import "./App.css";

const API = "http://localhost:8080/api/matches";

function App() {
  const [matches, setMatches] = useState([]);
  const [error, setError] = useState("");

  async function loadMatches() {
    try {
      const response = await fetch(API);
      if (!response.ok) throw new Error("Could not load matches");
      setMatches(await response.json());
      setError("");
    } catch {
      setError("Cannot connect to the backend. Check that Spring Boot is running.");
    }
  }

  async function updateScore(id, action, runs) {
    try {
      const url =
        action === "runs"
          ? `${API}/${id}/runs?runs=${runs}`
          : `${API}/${id}/wicket`;

      const response = await fetch(url, { method: "POST" });
      if (!response.ok) throw new Error("Score update failed");
      await loadMatches();
    } catch {
      setError("Could not update the score. Check your backend.");
    }
  }

  useEffect(() => {
    loadMatches();
    const timer = setInterval(loadMatches, 5000);
    return () => clearInterval(timer);
  }, []);

  return (
    <main className="app">
      <header className="header">
        <div className="ball-icon">🏏</div>
        <h1>CRICKET LIVE</h1>
        <p>Live Cricket Score Management System</p>
      </header>

      {error && <p className="error">{error}</p>}

      {matches.length === 0 && !error && <p>Loading matches...</p>}

      <section className="matches">
        {matches.map((match) => (
          <article className="score-card" key={match.id}>
            <div className="live-label">
              <span className="live-dot"></span> {match.status}
            </div>

            <h2>{match.teamA} <span>vs</span> {match.teamB}</h2>

            <div className="score">
              {match.runs}/{match.wickets}
            </div>

            <p className="overs">Overs: {match.overs}</p>

            <div className="score-buttons">
              {[0, 1, 2, 3, 4, 6].map((runs) => (
                <button
                  key={runs}
                  onClick={() => updateScore(match.id, "runs", runs)}
                  disabled={match.status !== "LIVE"}
                >
                  {runs === 0 ? "Dot" : `+${runs}`}
                </button>
              ))}

              <button
                className="wicket"
                onClick={() => updateScore(match.id, "wicket")}
                disabled={match.status !== "LIVE" || match.wickets >= 10}
              >
                Wicket
              </button>
            </div>

            <p className="refresh-note">Score refreshes automatically</p>
          </article>
        ))}
      </section>
    </main>
  );
}

export default App;
