import React, { useState } from 'react';
import axios from 'axios';

function App() {
  const [longUrl, setLongUrl] = useState('');
  const [shortUrl, setShortUrl] = useState('');
  const [analytics, setAnalytics] = useState(null);

  const handleShortenUrl = async () => {
    const response = await axios.post('http://localhost:8080/api/url/shorten', { longUrl });
    setShortUrl(response.data.shortUrl);
  };

  const handleAnalytics = async () => {
    const response = await axios.get(`http://localhost:8080/api/url/analytics/${shortUrl}`);
    setAnalytics(response.data);
  };

  return (
    <div className="App">
      <h1>URL Shortener</h1>
      <input
        type="text"
        placeholder="Enter Long URL"
        value={longUrl}
        onChange={(e) => setLongUrl(e.target.value)}
      />
      <button onClick={handleShortenUrl}>Shorten URL</button>

      {shortUrl && (
        <div>
          <h2>Shortened URL: http://localhost:8080/api/url/{shortUrl}</h2>
          <button onClick={handleAnalytics}>View Analytics</button>
        </div>
      )}

      {analytics && (
        <div>
          <h3>Analytics</h3>
          <p>Long URL: {analytics.longUrl}</p>
          <p>Clicks: {analytics.clickCount}</p>
        </div>
      )}
    </div>
  );
}

export default App;
