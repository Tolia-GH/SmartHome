import * as React from 'react';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import ListSubheader from '@mui/material/ListSubheader';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import HouseIcon from '@mui/icons-material/House';
import ReportIcon from '@mui/icons-material/Report';
import LogoutIcon from '@mui/icons-material/Logout';
import Diversity1Icon from '@mui/icons-material/Diversity1';


export const MainListItems = ({ onItemClick }) => {
    return (
        <React.Fragment>
            <ListItemButton onClick={() => onItemClick("My Profile")}>
                <ListItemIcon>
                    <AccountCircleIcon />
                </ListItemIcon>
                <ListItemText primary="My Profile" />
            </ListItemButton>
            <ListItemButton onClick={() => onItemClick("My Family")}>
                <ListItemIcon>
                    <Diversity1Icon />
                </ListItemIcon>
                <ListItemText primary="My Family" />
            </ListItemButton>
            <ListItemButton onClick={() => onItemClick("My Houses")}>
                <ListItemIcon>
                    <HouseIcon />
                </ListItemIcon>
                <ListItemText primary="My Houses" />
            </ListItemButton>
            <ListItemButton onClick={() => onItemClick("My Issues")}>
                <ListItemIcon>
                    <ReportIcon />
                </ListItemIcon>
                <ListItemText primary="My Issues" />
            </ListItemButton>
        </React.Fragment>
    );
};

export const SecondaryListItems = () => {
    return (
        <React.Fragment>
            <ListSubheader component="div" inset>
                {/* Placeholder for secondary list header */}
            </ListSubheader>
            <ListItemButton href="/">
                <ListItemIcon>
                    <LogoutIcon />
                </ListItemIcon>
                <ListItemText primary="Sign out" />
            </ListItemButton>
        </React.Fragment>
    );
};