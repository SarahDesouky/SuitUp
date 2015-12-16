class CommentsController < ApplicationController

skip_before_action :verify_authenticity_token

def getAllComments
@post = Post.where(:id=>params[:post_id])
@comments = Post.find(@post).comments.all
render json: @comments
end

def AddComment
  @comment = Comment.new(comment_params)
  if @comment.save 
    render json: @comment
  else 
    render json: @comment.errors, status: :unprocessable_entity 
  end
end

def comment_params
params.require(:comment).permit(:owner_id, :text, :post_id)
end
end