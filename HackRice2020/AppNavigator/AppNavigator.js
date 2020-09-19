import React from 'react';
import {Router, Stack, Scene} from 'react-native-router-flux';
import { StyleSheet} from 'react-native';

import HomePage from '../screens/HomePage';
<Scene key="login" component={LogIn} title="Login" initial={true}/>


export default class AppNavigator extends React.Component{
    render(){
        return(
            <Router>
			    <Stack key="root" hideNavBar={true}>
                    <Scene key="home" component={HomePage} title="HomePage" initial={true}/>
			    </Stack>
			 </Router>
        )
    }
}