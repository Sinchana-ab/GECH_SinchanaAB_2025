// components/VideoPlayer.jsx
import React from 'react';

const VideoPlayer = ({ src, title }) => {
  const isYouTube = src.includes('youtube.com') || src.includes('youtu.be');
  const isVimeo = src.includes('vimeo.com');

  const getEmbedUrl = () => {
    if (isYouTube) {
      const videoId = src.match(/(?:youtube\.com\/watch\?v=|youtu\.be\/)([^&]+)/)?.[1];
      return `https://www.youtube.com/embed/${videoId}`;
    }
    if (isVimeo) {
      const videoId = src.match(/vimeo\.com\/(\d+)/)?.[1];
      return `https://player.vimeo.com/video/${videoId}`;
    }
    return src;
  };

  if (isYouTube || isVimeo) {
    return (
      <div className="ratio ratio-16x9">
        <iframe
          src={getEmbedUrl()}
          title={title}
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowFullScreen
        ></iframe>
      </div>
    );
  }

  return (
    <video controls className="w-100" style={{ maxHeight: '500px' }}>
      <source src={src} />
      Your browser does not support the video tag.
    </video>
  );
};

export default VideoPlayer;