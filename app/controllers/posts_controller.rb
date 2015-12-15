class PostsController < ApplicationController

#returns a list of posts for a specific user
def getMyPosts
	@user = User.where(:twitter_id =>params[:twitter_id]).take
	@posts = @user.posts_on_own_wall.all
	render json: @posts
end

end
