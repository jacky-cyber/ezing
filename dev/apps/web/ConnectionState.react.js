import React from 'react';
import classnames from 'classnames';
import ConnectionStateStore from 'stores/ConnectionStateStore';

const getStateFromStore = () => {
  return {
    connectionState: ConnectionStateStore.getState()
  };
};

class ConnectionState extends React.Component {
  constructor(props) {
    super(props);

    this.state = getStateFromStore();

    ConnectionStateStore.addChangeListener(this.onStateChange);
  }

  componentWillUnmount() {
    ConnectionStateStore.removeChangeListener(this.onStateChange);
  }

  onStateChange = () => {
    this.setState(getStateFromStore);
  };

  render() {
    const { connectionState } = this.state;

    const className = classnames('connection-state', {
      'connection-state--online': connectionState === 'online',
      'connection-state--connection': connectionState === 'connecting'
    });

    switch (connectionState) {
      case 'online':
        return (
          <div className={className}>连接成功!</div>
        );
      case 'connecting':
        return (
          <div className={className}>
            与服务器的连接已断开。现尝试重新建立连接...
          </div>
        );
      default:
        return null;
    }
  }
}

export default ConnectionState;
