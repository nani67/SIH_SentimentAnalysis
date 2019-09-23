from instagram_private_api import Client, ClientCompatPatch

user_name = 'xxxxxxxxx'
password = 'xxxxxxxxx'

class InstagramScrape(object):

    def __init__(self, username, password):
        self.username = username
        self.password = password
        self.api = Client(username=username, password=password)

    def get_timeline(self):
        return self.api.feed_timeline()

    def get_self_feed(self):
        feed = self.api.self_feed()['items']
        result = []
        for entry in feed:
            if 'carousel_media_count' in list(entry.keys()):
                media = entry['carousel_media']
            else:
                media = [entry]
            carousel_parent_id = entry['id']
            text = entry['caption']['text']
            user_id = entry['caption']["user_id"]
            username = entry['user']['username']
            full_name = entry['user']['full_name']
            for medium in media:
                media_id = medium['id']
                images = medium['image_versions2']['candidates']
                for i in range(0, len(images)):
                    if not i % 2:
                        url = images[i]["url"]
                        image = {"carousel_parent_id": carousel_parent_id,
                                 "user_id": user_id,
                                 "username": username,
                                 "full_name": full_name,
                                 "media_id": media_id,
                                 "url": url,
                                 "text": text}
                        result.append(image)
        return result

    def get_own_stories(self):
        tray = self.api.reels_tray()['tray'][0]
        user_id = tray['id']
        username = tray['user']['username']
        full_name = tray['user']['full_name']
        stories = tray['items']

        result = []
        for story in stories:
            url = story['image_versions2']['candidates'][0]['url']
            entry = {"user_id": user_id,
                     "username": username,
                     "full_name": full_name,
                     "url": url}
            result.append(entry)

        return result

    def search_results(self, query):
        result = []
        search_results = self.api.search_users(query)['users']

        for user in search_results:
            pk = user['pk']
            username = user['username']
            full_name = user['full_name']
            info = {"id": pk,
                    "username": username,
                    "full_name": full_name}
            result.append(info)

        return result

    def get_explore_results(self):
        results = []
        explore_results = self.api.explore()['items']

        print(list(explore_results[0]))
        # return self.api.explore()

    def get_story_archive(self):
        return self.api.stories_archive()

    def get_top_search(self):
        return self.api.top_search()

    def gather_media_comments(self, id):
        return self.api.media_n_comments(id)


test = InstagramScrape(user_name, password)

feed = test.get_explore_results()
# print(feed)
