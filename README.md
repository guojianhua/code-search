# CodeSearch Plugin for IntelliJ IDEA-based IDEs

This plugin provides support for code search in IntelliJ IDEA, Android Studio, GoLand, WebStorm, PyCharm, CLion, PhpStorm, RubyMine, AppCode, DataGrip, Rider and MPS.

It provides search with Baidu, Google and StackOverflow. You can also customize the search service.

## Getting started

### How to install the plugin

#### Install plugin from repository

- Select **File | Setting... | Settings/Preferences | Plugins**
- Find **CodeSearch** plugin in the **Marketplace** and click **Install**.

#### Install plugin from disk

- Download **CodeSearch** plugin from [JetBrains Plugins Repository](https://plugins.jetbrains.com/plugin/12578-codesearch).
- Select **File | Setting... | Settings/Preferences | Plugins**.
- Click ![Settings Icon](https://github.com/guojianhua/code-search/blob/master/imgs/gearPlain.png) and then click **Install Plugin from Disk**.
- Select the plugin archive file and click **OK**.
- Click **OK** to apply the changes and restart the IDE if prompted.

### How to customize the search service

- Select **Code Search | Custom Search...** menu in the editor.
- Open **Code Search** Settings, click +.
- In the **Add Search Service** dialog
  * input search service name: **required**, for menu name.
  * input search service description: optional, for menu description.
  * specify search service URL: **required**. Click **Check** button to check the connection.
  * specify search service icon path: optional, recommend 16x16 png icon, for menu icon.
- Click **Add** to close **Add Search Service** dialog and save custom search service.
- Take effect after restart IDE.
- After restart IDE, you can see the menu with the name of the custom search service in the right-click **Code Search** menu bar of the editor.

![Add Search Service](https://github.com/guojianhua/code-search/blob/master/imgs/add-search-service.png)