// components/PDFViewer.jsx
import React from 'react';

const PDFViewer = ({ src, title }) => {
  return (
    <div className="border rounded" style={{ height: '600px' }}>
      <iframe
        src={src}
        title={title}
        className="w-100 h-100"
        style={{ border: 'none' }}
      />
    </div>
  );
};

export default PDFViewer;