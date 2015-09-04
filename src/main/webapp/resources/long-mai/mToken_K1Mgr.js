function mTokenMgr(obj){
	this.obj = obj;	
	
	var g_mTokenPlugin = null;

	this.LoadLibrary = function()
	{
		g_mTokenPlugin = document.getElementById(obj);

		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return 0;
	};

	this.K1Mgr_mTokenGetVersion = function()
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenGetVersion();
	};

	this.K1Mgr_mTokenFindDevice = function()
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenFindDevice();
	};

	this.K1Mgr_mTokenGetLastError = function()
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenGetLastError();
	};

	this.K1Mgr_mTokenGetUID = function(keyIndex)
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenGetUID(keyIndex);
	};

	this.K1Mgr_mTokenOpen = function(keyUID, keyPassword)
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenOpen(keyUID, keyPassword, 0);
	};

	this.K1Mgr_mTokenClose = function()
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenClose();
	};

	this.K1Mgr_mTokenChangePwd = function(keyUID,oldPassword, newPassword)
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenChangePwd(keyUID, 0, oldPassword, newPassword);
	};

	this.K1Mgr_mTokenSetSeedKey = function(keyUID, seedKey)
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenSetSeedKey(keyUID, seedKey);
	};

	this.K1Mgr_mTokenSetMainKey = function(keyUID, mainKey)
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenSetMainKey(keyUID, mainKey);
	};
	
	this.K1Mgr_mTokenGenResetpwdResponse = function(mainKey, clientRequest, adminPwd, newUserPwd)
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenGenResetpwdResponse(mainKey, clientRequest, adminPwd, newUserPwd);
	};

	this.K1Mgr_mTokenSetUserInfo = function(keyUID, openType, label, url, companyName, remarks, Tip)
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenSetUserInfo(keyUID, openType, label, url, companyName, remarks, Tip);
	};
	
	this.K1Mgr_mTokenUnlockPwd = function(keyUID, adminPwd, userPwd)
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenUnlockPwd(keyUID, adminPwd, userPwd);
	};
	
	this.K1Mgr_mTokenReadUserStorage = function(keyUID, offset, dataLength)
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenReadUserStorage(keyUID, offset, dataLength);
	};

	this.K1Mgr_mTokenWriteUserStorage = function(keyUID, offset, writeData)
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenWriteUserStorage(keyUID, offset, writeData);
	};

	
	this.K1Mgr_mTokenReadSecureStorageByAdmin = function(keyUID, offset, readLength)
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenReadSecureStorageByAdmin(keyUID, offset, readLength);
	};
	
	this.K1Mgr_mTokenWriteSecureStorageByAdmin = function(keyUID, offset, writeData)
	{
		if(g_mTokenPlugin == null)
		{
			return -1;
		}
		
		return g_mTokenPlugin.mTokenWriteSecureStorageByAdmin(keyUID, offset, writeData);
	};

}

