import React from 'react';
import { StyleSheet, Text, View, ImageBackground,Image, TouchableOpacity, Dimensions} from 'react-native';
import { Actions } from 'react-native-router-flux';
import { StatusBar } from 'expo-status-bar';


const windowWidth = Dimensions.get('window').width;
const windowHeight = Dimensions.get('window').height;

export default class HomePage extends React.Component{
    render() {
        return (
            <View style={styles.container}>
                <Text>Home Page</Text>
            
            </View>

        )
    }
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#fff',
      alignItems: 'center',
      justifyContent: 'center',
    },
});