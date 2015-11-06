import React from 'react';

class Deactivated extends React.Component {
  render() {
    return (
      <div className="deactivated row center-xs middle-xs">
        <div className="deactivated__window">
          <h2>此标签页失效</h2>
          <p>
            由于你在浏览器其他标签页中打开了易致，为了保证安全此标签页失效。
          </p>
        </div>
      </div>
    );
  }
}

export default Deactivated;
