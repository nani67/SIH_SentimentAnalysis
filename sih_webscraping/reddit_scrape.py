import praw

class RedditTarget(object):

    def __init__(self):
        self.client_id = 'nOU50agIaLiB_A'
        self.secret = 'VSW6KfIBnCyh2nJ0eMyLE52mY6s'
        self.user_agent = 'sih_test'
        self.reddit = praw.Reddit(client_id=self.client_id,
                                  client_secret=self.secret,
                                  user_agent=self.user_agent)

    def search_subreddit(self, term, params=50):
        return self.reddit.subreddit(term).top(limit=params)

    def get_posts_info(self, term):
        results = []
        search_result = self.search_subreddit(term)
        for term in search_result:

            author = 'anonymous'
            title = term.title
            text = term.selftext
            comments = term.comments.list()

            if term.author is not None:
                author = term.author.name

            result = {'author': author,
                      'title': title,
                      'text': text,
                      'comments': comments}
            results.append(result)

        return result

# just use get_posts_info for each illness.
# test = RedditTarget()
# test.get_posts_info('depression')