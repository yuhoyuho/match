import './App.css'

function App() {
  const apiBaseUrl = import.meta.env.VITE_API_BASE_URL ?? '/api'

  return (
    <main className="app-shell">
      <section className="intro">
        <p className="eyebrow">Development environment</p>
        <h1>REALMATCH+</h1>
        <p className="summary">
          React is ready to connect to the Spring API without adding product
          screens yet.
        </p>
      </section>

      <section className="status-grid" aria-label="Project setup status">
        <article>
          <span>Frontend</span>
          <strong>React + Vite</strong>
        </article>
        <article>
          <span>API proxy</span>
          <strong>{apiBaseUrl}</strong>
        </article>
        <article>
          <span>Backend</span>
          <strong>Spring Boot</strong>
        </article>
      </section>
    </main>
  )
}

export default App
