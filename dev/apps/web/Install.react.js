import React from 'react';

export default class Install extends React.Component {
  render() {
    return (
      <section className="mobile-placeholder col-xs row center-xs middle-xs">
        <div>
          <img alt="eZing messenger"
               className="logo"
               src="assets/img/logo.png"
               srcSet="assets/img/logo@2x.png 2x"/>


          <h1>PC Web版<b>易致</b>仅适用于PC浏览器</h1>
          <h3>手机上请安装<b>易致</b>手机版应用：</h3>
          <p>
            <a href="//app.ezing.cn">iPhone</a> | <a href="//app.ezing.cn">Android</a>
          </p>
        </div>
      </section>
    );
  }
}
